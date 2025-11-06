#!/bin/bash

echo "🛑 DETENIENDO SKYNET MICROSERVICES"
echo "=================================="

# Detener servicios
echo "⏸️  Deteniendo servicios..."
docker-compose down

echo "✅ Servicios detenidos"
echo ""
echo "Para eliminar también los volúmenes (datos), ejecuta:"
echo "docker-compose down -v"
