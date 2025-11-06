#!/bin/bash

echo "🧪 EJECUTANDO PRUEBAS AUTOMATIZADAS"
echo "==================================="

# Función para probar un endpoint
test_endpoint() {
    local url=$1
    local name=$2
    echo "🔍 Probando $name..."
    
    if curl -f -s "$url" > /dev/null; then
        echo "✅ $name - FUNCIONA"
        return 0
    else
        echo "❌ $name - FALLÓ"
        return 1
    fi
}

# Probar servicios
echo "📡 Probando microservicios..."
test_endpoint "http://localhost:8081/health" "Auth Service"
test_endpoint "http://localhost:8082/health" "Client Service" 
test_endpoint "http://localhost:8083/health" "Visit Service"

# Probar APIs principales
echo "🔧 Probando APIs..."
test_endpoint "http://localhost:8081/api/auth/usuarios/admin@skynet.com" "Auth API"
test_endpoint "http://localhost:8082/api/clientes" "Clientes API"
test_endpoint "http://localhost:8083/api/visitas" "Visitas API"

# Probar frontend
echo "🎨 Probando frontend..."
test_endpoint "http://localhost:3000" "Frontend React"

echo "🎉 Pruebas completadas!"
