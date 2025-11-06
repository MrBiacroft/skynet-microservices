#!/bin/bash

echo "🧪 TESTING SKYNET AUTH SERVICE API"
echo "===================================="
echo ""

# Test 1: POST /api/auth/login - Admin
echo "📝 Test 1: Login as ADMIN"
echo "POST http://localhost:8081/api/auth/login"
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@skynet.com","password":"123456"}' \
  -s | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 2: POST /api/auth/login - Supervisor
echo "📝 Test 2: Login as SUPERVISOR"
echo "POST http://localhost:8081/api/auth/login"
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"supervisor@skynet.com","password":"123456"}' \
  -s | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 3: POST /api/auth/login - Tecnico
echo "📝 Test 3: Login as TECNICO"
echo "POST http://localhost:8081/api/auth/login"
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"tecnico@skynet.com","password":"123456"}' \
  -s | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 4: POST /api/auth/login - Invalid credentials
echo "📝 Test 4: Login with INVALID credentials"
echo "POST http://localhost:8081/api/auth/login"
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@skynet.com","password":"wrong"}' \
  -s | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 5: GET /api/auth/usuarios/{email}
echo "📝 Test 5: Get user by email"
echo "GET http://localhost:8081/api/auth/usuarios/admin@skynet.com"
curl -s "http://localhost:8081/api/auth/usuarios/admin%40skynet.com" | python3 -m json.tool
echo ""
echo "---"
echo ""

echo "✅ ALL TESTS COMPLETED"
