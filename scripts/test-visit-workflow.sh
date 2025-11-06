#!/bin/bash

echo "🧪 TESTING VISIT SERVICE - COMPLETE WORKFLOW"
echo "=============================================="
echo ""

BASE_URL="http://localhost:8083"

# Test 1: Get today's visits for technician
echo "📝 Test 1: Get today's visits for technician 3"
echo "GET ${BASE_URL}/api/visitas/tecnicos/3/hoy"
curl -s "${BASE_URL}/api/visitas/tecnicos/3/hoy" | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 2: Check-in to visit 1
echo "📝 Test 2: Check-in to visit 1 (registrar ingreso)"
echo "POST ${BASE_URL}/api/visitas/1/registrar-ingreso"
curl -X POST "${BASE_URL}/api/visitas/1/registrar-ingreso" \
  -H "Content-Type: application/json" \
  -d '{
    "latitud": 14.6039,
    "longitud": -90.5068
  }' -s | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 3: Verify visit status changed to EN_CURSO
echo "📝 Test 3: Verify visit status changed to EN_CURSO"
echo "GET ${BASE_URL}/api/visitas/1"
curl -s "${BASE_URL}/api/visitas/1" | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 4: Check-out from visit 1 with report
echo "📝 Test 4: Check-out from visit 1 (registrar egreso)"
echo "POST ${BASE_URL}/api/visitas/1/registrar-egreso"
curl -X POST "${BASE_URL}/api/visitas/1/registrar-egreso" \
  -H "Content-Type: application/json" \
  -d '{
    "reporte": "Visita completada exitosamente. Se realizó mantenimiento preventivo del equipo. Todo funcionando correctamente."
  }' -s | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 5: Verify visit status changed to COMPLETADA
echo "📝 Test 5: Verify visit status changed to COMPLETADA"
echo "GET ${BASE_URL}/api/visitas/1"
curl -s "${BASE_URL}/api/visitas/1" | python3 -m json.tool
echo ""
echo "---"
echo ""

# Test 6: Get visits by status COMPLETADA
echo "📝 Test 6: Get completed visits"
echo "GET ${BASE_URL}/api/visitas/estado/COMPLETADA"
curl -s "${BASE_URL}/api/visitas/estado/COMPLETADA" | python3 -m json.tool
echo ""
echo "---"
echo ""

echo "✅ ALL TESTS COMPLETED"
echo ""
echo "📧 Check the service console for the email notification that was sent!"
