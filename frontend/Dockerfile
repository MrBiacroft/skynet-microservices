FROM node:18-alpine AS build
WORKDIR /app

# Install dependencies first (better caching)
COPY package*.json ./
RUN npm ci --only=production=false

# Copy source code
COPY . .

# Build arguments for API URLs
ARG VITE_API_AUTH_URL
ARG VITE_API_CLIENT_URL
ARG VITE_API_VISIT_URL

# Set environment variables for Vite build
ENV VITE_API_AUTH_URL=$VITE_API_AUTH_URL
ENV VITE_API_CLIENT_URL=$VITE_API_CLIENT_URL
ENV VITE_API_VISIT_URL=$VITE_API_VISIT_URL

# Build the application
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
