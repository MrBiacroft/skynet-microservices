#!/bin/bash
echo "🔍 VERIFICACIÓN DE CONFIGURACIÓN SKYNET MICROSERVICES"
echo "======================================================"

# Verificar herramientas
echo "✅ Java: $(java -version 2>&1 | head -1)"
echo "✅ Node: $(node --version)"
echo "✅ NPM: $(npm --version)"

# Verificar estructura
echo ""
echo "📁 ESTRUCTURA DE CARPETAS:"
if [ -d "microservicios" ]; then
  echo "✅ microservicios/"
  ls -la microservicios/
else
  echo "❌ microservicios/ - NO EXISTE"
fi

if [ -d "frontend" ]; then
  echo "✅ frontend/"
else
  echo "❌ frontend/ - NO EXISTE"
fi

echo ""
echo "🎯 CONFIGURACIÓN COMPLETADA - LISTO PARA FASE 2"
