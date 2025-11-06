#!/bin/bash

echo "🧪 TESTING SKYNET CLIENT SERVICE API"
echo "====================================="
echo ""

# Test 1: GET all clients
echo "📝 Test 1: Get all clients"
echo "GET http://localhost:8082/api/clientes"
curl -s http://localhost:8082/api/clientes | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 2: GET client by ID
echo "📝 Test 2: Get client by ID"
echo "GET http://localhost:8082/api/clientes/1"
curl -s http://localhost:8082/api/clientes/1 | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 3: Search clients by name
echo "📝 Test 3: Search clients by name"
echo "GET http://localhost:8082/api/clientes/buscar?nombre=ABC"
curl -s "http://localhost:8082/api/clientes/buscar?nombre=ABC" | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 4: Get clients with geolocation
echo "📝 Test 4: Get clients with geolocation"
echo "GET http://localhost:8082/api/clientes/geolocalizacion"
curl -s http://localhost:8082/api/clientes/geolocalizacion | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 5: Create new client
echo "📝 Test 5: Create new client"
echo "POST http://localhost:8082/api/clientes"
curl -X POST http://localhost:8082/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Nuevo Cliente Test",
    "direccion": "Dirección de prueba",
    "telefono": "9999-9999",
    "email": "test@cliente.com",
    "latitud": 14.6000,
    "longitud": -90.5000
  }' \
  -s | python3 -m json.tool
echo ""
echo "---"
echo ""

echo "✅ ALL TESTS COMPLETED"
