// Configuración de URLs de API
// Producción: URLs de Railway
const isProd = window.location.hostname !== 'localhost';

export const API_URLS = {
  AUTH: isProd ? 'https://auth-servic-production.up.railway.app' : 'http://localhost:8081',
  CLIENT: isProd ? 'https://client-servic-production.up.railway.app' : 'http://localhost:8082',
  VISIT: isProd ? 'https://visit.up.railway.app' : 'http://localhost:8083'
};

// Helper para construir URLs completas
export const getApiUrl = (service, endpoint) => {
  const baseUrl = API_URLS[service.toUpperCase()];
  return `${baseUrl}${endpoint}`;
};
