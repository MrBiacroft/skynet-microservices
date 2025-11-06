#!/bin/bash

echo "🚀 INICIANDO DESPLIEGUE SKYNET MICROSERVICES"
echo "============================================"

# Verificar que Docker esté instalado
if ! command -v docker &> /dev/null; then
    echo "❌ Docker no está instalado"
    exit 1
fi

# Verificar que Docker Compose esté instalado
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose no está instalado"
    exit 1
fi

# Construir y levantar los servicios
echo "🔨 Construyendo imágenes Docker..."
docker-compose build

echo "🎯 Iniciando servicios..."
docker-compose up -d

echo "⏳ Esperando a que los servicios estén listos..."
sleep 30

# Verificar que los servicios estén corriendo
echo "🔍 Verificando servicios..."
docker-compose ps

echo "✅ Despliegue completado!"
echo "🌐 Frontend: http://localhost:3000"
echo "🔧 Auth Service: http://localhost:8081"
echo "🏢 Client Service: http://localhost:8082"
echo "📅 Visit Service: http://localhost:8083"
echo "🗄️  Database: PostgreSQL (localhost:5432)"
echo ""
echo "📊 Verificar datos en PostgreSQL:"
echo "   docker exec skynet-postgres psql -U postgres -d skynet"
