import React from 'react'
import { Settings, Users, Database, Shield } from 'lucide-react'

function AdminDashboard() {
  return (
    <div>
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">Dashboard Administrador</h1>
        <p className="text-gray-600 mt-2">Administración completa del sistema</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <div className="card text-center hover:shadow-lg transition-shadow cursor-pointer">
          <Users className="h-8 w-8 text-blue-600 mx-auto mb-3" />
          <h3 className="font-semibold text-gray-900">Gestión de Usuarios</h3>
          <p className="text-sm text-gray-600 mt-1">Administrar roles y permisos</p>
        </div>

        <div className="card text-center hover:shadow-lg transition-shadow cursor-pointer">
          <Database className="h-8 w-8 text-green-600 mx-auto mb-3" />
          <h3 className="font-semibold text-gray-900">Clientes</h3>
          <p className="text-sm text-gray-600 mt-1">Gestionar base de clientes</p>
        </div>

        <div className="card text-center hover:shadow-lg transition-shadow cursor-pointer">
          <Shield className="h-8 w-8 text-red-600 mx-auto mb-3" />
          <h3 className="font-semibold text-gray-900">Seguridad</h3>
          <p className="text-sm text-gray-600 mt-1">Configuración de accesos</p>
        </div>

        <div className="card text-center hover:shadow-lg transition-shadow cursor-pointer">
          <Settings className="h-8 w-8 text-purple-600 mx-auto mb-3" />
          <h3 className="font-semibold text-gray-900">Configuración</h3>
          <p className="text-sm text-gray-600 mt-1">Ajustes del sistema</p>
        </div>
      </div>

      <div className="card">
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Panel de Administración</h2>
        <p className="text-gray-600">
          Módulo de administración completo en desarrollo. Incluirá:
        </p>
        <ul className="list-disc list-inside text-gray-600 mt-2 space-y-1">
          <li>Gestión de usuarios y roles</li>
          <li>Configuración del sistema</li>
          <li>Reportes avanzados</li>
          <li>Auditoría y logs</li>
          <li>Integración con otros sistemas</li>
        </ul>
      </div>
    </div>
  )
}

export default AdminDashboard
