import React, { useState, useEffect } from 'react'
import { Calendar, MapPin, Clock, CheckCircle, PlayCircle } from 'lucide-react'
import axios from 'axios'
import { API_URLS } from '../../config'

function TecnicoDashboard() {
  const [visitasHoy, setVisitasHoy] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    cargarVisitasHoy()
  }, [])

  const cargarVisitasHoy = async () => {
    try {
      // En una app real, obtendríamos el ID del técnico del contexto de auth
      const response = await axios.get(`${API_URLS.VISIT}/api/visitas/tecnicos/3/hoy`)
      setVisitasHoy(response.data)
    } catch (error) {
      console.error('Error cargando visitas:', error)
    } finally {
      setLoading(false)
    }
  }

  const registrarIngreso = async (visitaId) => {
    try {
      // Simulamos coordenadas (en una app real, usaríamos GPS del dispositivo)
      const coordenadas = {
        latitud: 14.6039 + (Math.random() - 0.5) * 0.01,
        longitud: -90.5068 + (Math.random() - 0.5) * 0.01
      }

      await axios.post(`${API_URLS.VISIT}/api/visitas/${visitaId}/registrar-ingreso`, coordenadas)
      cargarVisitasHoy() // Recargar datos
      alert('✅ Ingreso registrado correctamente')
    } catch (error) {
      alert('❌ Error registrando ingreso')
    }
  }

  const getEstadoStyles = (estado) => {
    const styles = {
      'PLANIFICADA': 'bg-yellow-100 text-yellow-800 border-yellow-200',
      'EN_CURSO': 'bg-blue-100 text-blue-800 border-blue-200',
      'COMPLETADA': 'bg-green-100 text-green-800 border-green-200',
      'CANCELADA': 'bg-red-100 text-red-800 border-red-200'
    }
    return styles[estado] || 'bg-gray-100 text-gray-800 border-gray-200'
  }

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="text-lg text-gray-600">Cargando visitas...</div>
      </div>
    )
  }

  return (
    <div>
      {/* Header del Dashboard */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">Dashboard Técnico</h1>
        <p className="text-gray-600 mt-2">Gestión de visitas asignadas para hoy</p>
      </div>

      {/* Estadísticas Rápidas */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div className="card">
          <div className="flex items-center">
            <div className="bg-blue-100 p-3 rounded-lg">
              <Calendar className="h-6 w-6 text-blue-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Total Visitas Hoy</p>
              <p className="text-2xl font-bold text-gray-900">{visitasHoy.length}</p>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="flex items-center">
            <div className="bg-green-100 p-3 rounded-lg">
              <CheckCircle className="h-6 w-6 text-green-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Completadas</p>
              <p className="text-2xl font-bold text-gray-900">
                {visitasHoy.filter(v => v.estado === 'COMPLETADA').length}
              </p>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="flex items-center">
            <div className="bg-yellow-100 p-3 rounded-lg">
              <Clock className="h-6 w-6 text-yellow-600" />
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600">Pendientes</p>
              <p className="text-2xl font-bold text-gray-900">
                {visitasHoy.filter(v => v.estado === 'PLANIFICADA').length}
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Lista de Visitas */}
      <div className="card">
        <h2 className="text-xl font-semibold text-gray-900 mb-6">Visitas de Hoy</h2>
        
        {visitasHoy.length === 0 ? (
          <div className="text-center py-8">
            <Calendar className="h-12 w-12 text-gray-400 mx-auto mb-4" />
            <p className="text-gray-600">No hay visitas programadas para hoy</p>
          </div>
        ) : (
          <div className="space-y-4">
            {visitasHoy.map((visita) => (
              <div key={visita.id} className="border border-gray-200 rounded-lg p-4 hover:shadow-md transition-shadow">
                <div className="flex justify-between items-start">
                  <div className="flex-1">
                    <div className="flex items-center justify-between mb-2">
                      <h3 className="text-lg font-medium text-gray-900">{visita.clienteNombre}</h3>
                      <span className={`inline-flex items-center px-3 py-1 rounded-full text-sm font-medium border ${getEstadoStyles(visita.estado)}`}>
                        {visita.estado}
                      </span>
                    </div>
                    
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm text-gray-600">
                      <div className="flex items-center space-x-2">
                        <MapPin className="h-4 w-4 text-gray-400" />
                        <span>{visita.clienteDireccion}</span>
                      </div>
                      <div className="flex items-center space-x-2">
                        <Clock className="h-4 w-4 text-gray-400" />
                        <span>{visita.horaPlanificada}</span>
                      </div>
                    </div>

                    {visita.estado === 'COMPLETADA' && visita.reporte && (
                      <div className="mt-3 p-3 bg-gray-50 rounded-lg">
                        <p className="text-sm text-gray-700">
                          <strong>Reporte:</strong> {visita.reporte}
                        </p>
                      </div>
                    )}
                  </div>

                  <div className="ml-4 flex flex-col space-y-2">
                    {visita.estado === 'PLANIFICADA' && (
                      <button
                        onClick={() => registrarIngreso(visita.id)}
                        className="flex items-center space-x-2 bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg transition-colors"
                      >
                        <PlayCircle className="h-4 w-4" />
                        <span>Iniciar Visita</span>
                      </button>
                    )}
                    
                    {visita.estado === 'EN_CURSO' && (
                      <button
                        onClick={() => alert('Funcionalidad de completar visita en desarrollo...')}
                        className="flex items-center space-x-2 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg transition-colors"
                      >
                        <CheckCircle className="h-4 w-4" />
                        <span>Completar</span>
                      </button>
                    )}

                    <a
                      href={`https://maps.google.com/?q=${visita.clienteLatitud},${visita.clienteLongitud}`}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="flex items-center space-x-2 bg-gray-600 hover:bg-gray-700 text-white px-4 py-2 rounded-lg transition-colors"
                    >
                      <MapPin className="h-4 w-4" />
                      <span>Ver en Maps</span>
                    </a>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}

export default TecnicoDashboard
