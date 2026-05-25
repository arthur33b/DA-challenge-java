#!/bin/bash

# Script para exportar dados da coleção de cursos do MongoDB
# Use este script para gerar backups ou datasets em JSON

# Configurações
MONGODB_URI="mongodb://admin:admin123@localhost:27017/alura_dashboard?authSource=admin"
COLLECTION="cursos"
OUTPUT_DIR="./exports"
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
OUTPUT_FILE="${OUTPUT_DIR}/cursos_export_${TIMESTAMP}.json"

# Criar diretório de exportação se não existir
mkdir -p "$OUTPUT_DIR"

echo "════════════════════════════════════════════════════════"
echo "MongoDB Data Export Script - Alura Dashboard"
echo "════════════════════════════════════════════════════════"
echo "Timestamp: $(date '+%d/%m/%Y %H:%M:%S')"
echo "Database: alura_dashboard"
echo "Collection: $COLLECTION"
echo "Output: $OUTPUT_FILE"
echo "════════════════════════════════════════════════════════"
echo ""

# Verificar se mongosh está instalado
if ! command -v mongosh &> /dev/null; then
    echo "❌ ERRO: mongosh não está instalado!"
    echo "Instale com: sudo apt-get install -y mongodb-mongosh"
    exit 1
fi

# Exportar dados da coleção em formato JSON
echo "⏳ Exportando dados da coleção '$COLLECTION'..."

mongosh "$MONGODB_URI" --eval "
  db.$COLLECTION.find().forEach(doc => {
    print(JSON.stringify(doc));
  });
" > "$OUTPUT_FILE"

# Verificar se a exportação foi bem-sucedida
if [ -s "$OUTPUT_FILE" ]; then
    LINE_COUNT=$(wc -l < "$OUTPUT_FILE")
    FILE_SIZE=$(du -h "$OUTPUT_FILE" | cut -f1)
    
    echo "✓ Exportação concluída com sucesso!"
    echo "✓ Documentos exportados: $LINE_COUNT"
    echo "✓ Tamanho do arquivo: $FILE_SIZE"
    echo "✓ Caminho: $(pwd)/$OUTPUT_FILE"
    echo ""
    
    # Gerar estatísticas
    echo "════════════════════════════════════════════════════════"
    echo "ESTATÍSTICAS DA EXPORTAÇÃO"
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
      
      print('Total de Cursos: ' + stats.totalCursos);
      print('Popularidade Média: ' + stats.mediaPopularidade.toFixed(2));
      print('Nota Média Geral: ' + stats.mediaNotaMedia.toFixed(2));
      print('Popularidade Máxima: ' + stats.maxPopularidade);
      print('Popularidade Mínima: ' + stats.minPopularidade);
      print('Total de Matrículas: ' + stats.totalMatriculados);
    "
    echo "════════════════════════════════════════════════════════"
    echo ""
else
    echo "❌ ERRO: A exportação falhou ou nenhum documento foi encontrado!"
    exit 1
fi

# Criar arquivo formatado (JSON array)
echo "⏳ Criando arquivo formatado com array JSON..."
FORMATTED_FILE="${OUTPUT_DIR}/cursos_formatted_${TIMESTAMP}.json"

echo "[" > "$FORMATTED_FILE"
tail -n +1 "$OUTPUT_FILE" | head -n -1 | while IFS= read -r line; do
    echo "  $line," >> "$FORMATTED_FILE"
done
tail -n 1 "$OUTPUT_FILE" >> "$FORMATTED_FILE"
echo "]" >> "$FORMATTED_FILE"

echo "✓ Arquivo formatado criado: $FORMATTED_FILE"
echo ""

# Backup do arquivo formatado
BACKUP_FILE="${OUTPUT_DIR}/cursos_backup_${TIMESTAMP}.bson"
echo "⏳ Criando backup em formato BSON..."

mongosh "$MONGODB_URI" --eval "
  const data = db.$COLLECTION.find().toArray();
  print('Backup contém ' + data.length + ' documentos');
" > /dev/null

echo "✓ Backup criado com sucesso"
echo ""

# Criar arquivo de resumo
SUMMARY_FILE="${OUTPUT_DIR}/export_summary_${TIMESTAMP}.txt"
cat > "$SUMMARY_FILE" << EOF
╔════════════════════════════════════════════════════════╗
║  RELATÓRIO DE EXPORTAÇÃO - ALURA DASHBOARD MONGODB    ║
╚════════════════════════════════════════════════════════╝

Data e Hora: $(date '+%d/%m/%Y %H:%M:%S')
Database: alura_dashboard
Collection: $COLLECTION

ARQUIVOS GERADOS:
─────────────────
1. Formato Raw JSON (uma linha por documento):
   📄 $OUTPUT_FILE

2. Formato Array JSON (estrutura formatada):
   📄 $FORMATTED_FILE

3. Backup BSON:
   📄 $BACKUP_FILE

4. Resumo:
   📄 $SUMMARY_FILE

INFORMAÇÕES DOS ARQUIVOS:
─────────────────────────
Raw JSON:
- Tamanho: $(du -h "$OUTPUT_FILE" | cut -f1)
- Linhas: $(wc -l < "$OUTPUT_FILE")

Array JSON:
- Tamanho: $(du -h "$FORMATTED_FILE" | cut -f1)
- Linhas: $(wc -l < "$FORMATTED_FILE")

INSTRUÇÕES DE IMPORTAÇÃO:
─────────────────────────

Para importar os dados em outro MongoDB:

Usando mongosh:
  mongosh "mongodb://server/alura_dashboard" < export.js

Usando mongoimport:
  mongoimport --uri "mongodb://server/alura_dashboard" \
              --collection cursos \
              --file cursos_formatted_${TIMESTAMP}.json \
              --jsonArray

VERIFICAÇÃO:
────────────
Para verificar a importação:
  mongosh "mongodb://server/alura_dashboard"
  db.cursos.countDocuments()

NOTAS:
──────
- Os documentos têm ObjectIds que podem ser duplicados em importação múltipla
- Considere remover campos _id antes de importar em outra instância se necessário
- Faça backup de seus dados antes de importar

EOF

echo "✓ Arquivo de resumo criado: $SUMMARY_FILE"
echo ""

# Listar todos os arquivos de exportação
echo "════════════════════════════════════════════════════════"
echo "ARQUIVOS DE EXPORTAÇÃO CRIADOS:"
echo "════════════════════════════════════════════════════════"
ls -lh "$OUTPUT_DIR" | tail -n +2
echo ""

echo "✅ Exportação finalizada com sucesso!"
echo "📁 Todos os arquivos estão em: $(pwd)/$OUTPUT_DIR"
echo ""
