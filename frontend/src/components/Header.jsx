import React from 'react'
import { Building2, LogOut, User } from 'lucide-react'

function Header({ user, onLogout }) {
  const getRoleBadge = (rol) => {
    const roles = {
      'ADMIN': 'bg-red-100 text-red-800',
      'SUPERVISOR': 'bg-blue-100 text-blue-800',
      'TECNICO': 'bg-green-100 text-green-800'
    }
    return roles[rol] || 'bg-gray-100 text-gray-800'
  }

  return (
    <header className="bg-white shadow-sm border-b">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo y título */}
          <div className="flex items-center space-x-3">
            <div className="bg-skynet-primary rounded-lg p-2">
              <Building2 className="h-6 w-6 text-white" />
            </div>
            <div>
              <h1 className="text-xl font-semibold text-gray-900">SkyNet S.A.</h1>
              <p className="text-sm text-gray-500">Sistema de Gestión de Visitas</p>
            </div>
          </div>

          {/* Información del usuario */}
          <div className="flex items-center space-x-4">
            <div className="text-right">
              <div className="flex items-center space-x-2">
                <User className="h-4 w-4 text-gray-400" />
                <span className="font-medium text-gray-900">{user.nombre}</span>
              </div>
              <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getRoleBadge(user.rol)}`}>
                {user.rol}
              </span>
            </div>
            
            <button
              onClick={onLogout}
              className="flex items-center space-x-2 text-gray-500 hover:text-gray-700 transition-colors"
            >
              <LogOut className="h-5 w-5" />
              <span className="hidden sm:block">Cerrar Sesión</span>
            </button>
          </div>
        </div>
      </div>
    </header>
  )
}

export default Header
