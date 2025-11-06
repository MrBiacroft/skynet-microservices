// Configuración de URLs de API
// En desarrollo usa localhost, en producción usa variables de entorno

export const API_URLS = {
  AUTH: import.meta.env.VITE_API_AUTH_URL || 'http://localhost:8081',
  CLIENT: import.meta.env.VITE_API_CLIENT_URL || 'http://localhost:8082',
  VISIT: import.meta.env.VITE_API_VISIT_URL || 'http://localhost:8083'
};

// Helper para construir URLs completas
export const getApiUrl = (service, endpoint) => {
  const baseUrl = API_URLS[service.toUpperCase()];
  return `${baseUrl}${endpoint}`;
};
