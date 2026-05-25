#!/bin/bash

# Script para importar dados de cursos no MongoDB
# Use este script para popular a database com dados da exportação

# Configurações
MONGODB_URI="mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin"
COLLECTION="cursos"
IMPORT_DIR="./exports"

echo "════════════════════════════════════════════════════════"
echo "MongoDB Data Import Script - Alura Dashboard"
echo "════════════════════════════════════════════════════════"
echo ""

# Verificar se mongoimport está instalado
if ! command -v mongoimport &> /dev/null; then
    echo "❌ ERRO: mongoimport não está instalado!"
    echo "Instale com: sudo apt-get install -y mongodb-tools"
    exit 1
fi

# Se nenhum arquivo foi especificado, procurar pelo mais recente
if [ -z "$1" ]; then
    IMPORT_FILE=$(ls -t "$IMPORT_DIR"/cursos_formatted_*.json 2>/dev/null | head -1)
    if [ -z "$IMPORT_FILE" ]; then
        echo "❌ ERRO: Nenhum arquivo de importação encontrado em $IMPORT_DIR"
        echo "Use: bash import_mongodb_data.sh [caminho_do_arquivo.json]"
        exit 1
    fi
else
    IMPORT_FILE="$1"
fi

echo "Arquivo de importação: $IMPORT_FILE"
echo "Database: alura_dashboard"
echo "Collection: $COLLECTION"
echo ""

# Verificar se o arquivo existe
if [ ! -f "$IMPORT_FILE" ]; then
    echo "❌ ERRO: Arquivo não encontrado: $IMPORT_FILE"
    exit 1
fi

# Contar documentos antes da importação
echo "⏳ Obtendo contagem antes da importação..."
DOCS_BEFORE=$(mongosh "$MONGODB_URI" --eval "db.$COLLECTION.countDocuments()" --quiet)
echo "Documentos antes: $DOCS_BEFORE"
echo ""

# Perguntar se deseja substituir os dados existentes
echo "⚠️  AVISO: Esta operação irá SUBSTITUIR os dados existentes na coleção '$COLLECTION'"
read -p "Deseja continuar? (s/n): " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Ss]$ ]]; then
    echo "❌ Importação cancelada"
    exit 1
fi

# Limpar a coleção existente
echo "⏳ Limpando coleção existente..."
mongosh "$MONGODB_URI" --eval "db.$COLLECTION.deleteMany({})" > /dev/null
echo "✓ Coleção limpa"
echo ""

# Importar os dados
echo "⏳ Importando dados do arquivo..."
mongoimport \
    --uri "$MONGODB_URI" \
    --collection "$COLLECTION" \
    --file "$IMPORT_FILE" \
    --jsonArray \
    --upsert

# Verificar resultado
IMPORT_STATUS=$?

if [ $IMPORT_STATUS -eq 0 ]; then
    # Contar documentos após a importação
    DOCS_AFTER=$(mongosh "$MONGODB_URI" --eval "db.$COLLECTION.countDocuments()" --quiet)
    echo ""
    echo "════════════════════════════════════════════════════════"
    echo "✅ IMPORTAÇÃO CONCLUÍDA COM SUCESSO!"
    echo "════════════════════════════════════════════════════════"
    echo "Documentos importados: $DOCS_AFTER"
    echo ""
    
    # Gerar estatísticas
    echo "════════════════════════════════════════════════════════"
    echo "ESTATÍSTICAS PÓS-IMPORTAÇÃO"
    echo "════════════════════════════════════════════════════════"
    
    mongosh "$MONGODB_URI" --eval "
      const stats = db.$COLLECTION.aggregate([
        {
          \\\$group: {
            _id: null,
            totalCursos: { \\\$sum: 1 },
            mediaPopularidade: { \\\$avg: '\\\$popularidade' },
            mediaNotaMedia: { \\\$avg: '\\\$notaMedia' },
            maxPopularidade: { \\\$max: '\\\$popularidade' },
            minPopularidade: { \\\$min: '\\\$popularidade' },
            totalMatriculados: { \\\$sum: '\\\$matriculados' }
          }
        }
      ]).toArray()[0];
      
      if (stats) {
        print('Total de Cursos: ' + stats.totalCursos);
        print('Popularidade Média: ' + stats.mediaPopularidade.toFixed(2));
        print('Nota Média Geral: ' + stats.mediaNotaMedia.toFixed(2));
        print('Popularidade Máxima: ' + stats.maxPopularidade);
        print('Popularidade Mínima: ' + stats.minPopularidade);
        print('Total de Matrículas: ' + stats.totalMatriculados);
      }
    "
    echo "════════════════════════════════════════════════════════"
    echo ""
else
    echo "❌ ERRO: A importação falhou!"
    exit 1
fi
