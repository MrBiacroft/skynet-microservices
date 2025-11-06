import React, { createContext, useState, useContext, useEffect } from 'react'
import axios from 'axios'
import { API_URLS } from '../config'

const AuthContext = createContext()

export function useAuth() {
  return useContext(AuthContext)
}

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  // Configurar axios base URL
  useEffect(() => {
    axios.defaults.baseURL = API_URLS.AUTH
  }, [])

  const login = async (email, password) => {
    try {
      const response = await axios.post('/api/auth/login', {
        email,
        password
      })
      
      if (response.data.token) {
        const userData = response.data.usuario
        setUser(userData)
        localStorage.setItem('user', JSON.stringify(userData))
        localStorage.setItem('token', response.data.token)
        return { success: true, data: response.data }
      }
    } catch (error) {
      return { 
        success: false, 
        error: error.response?.data?.mensaje || 'Error de conexión' 
      }
    }
  }

  const logout = () => {
    setUser(null)
    localStorage.removeItem('user')
    localStorage.removeItem('token')
  }

  // Verificar si hay usuario en localStorage al cargar la app
  useEffect(() => {
    const savedUser = localStorage.getItem('user')
    if (savedUser) {
      setUser(JSON.parse(savedUser))
    }
    setLoading(false)
  }, [])

  const value = {
    user,
    login,
    logout,
    loading
  }

  return (
    <AuthContext.Provider value={value}>
      {!loading && children}
    </AuthContext.Provider>
  )
}
