#!/bin/bash
echo "🚀 INICIANDO BACKEND - SKYNET MICROSERVICES"
echo "==========================================="

cd microservicios/auth-service

echo "🔧 Compilando proyecto..."
./mvnw clean compile

echo "🎯 Iniciando Auth Service..."
./mvnw spring-boot:run
