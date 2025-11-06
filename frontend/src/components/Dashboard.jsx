import React from 'react'
import { useAuth } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'
import Header from './Header'
import AdminDashboard from './dashboards/AdminDashboard'
import SupervisorDashboard from './dashboards/SupervisorDashboard'
import TecnicoDashboard from './dashboards/TecnicoDashboard'

function Dashboard() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  const renderDashboard = () => {
    switch (user.rol) {
      case 'ADMIN':
        return <AdminDashboard />
      case 'SUPERVISOR':
        return <SupervisorDashboard />
      case 'TECNICO':
        return <TecnicoDashboard />
      default:
        return <div>Rol no reconocido</div>
    }
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Header user={user} onLogout={handleLogout} />
      <main className="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
        {renderDashboard()}
      </main>
    </div>
  )
}

export default Dashboard
