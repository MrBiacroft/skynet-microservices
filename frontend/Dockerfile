FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

# Build arguments for API URLs
ARG VITE_API_AUTH_URL
ARG VITE_API_CLIENT_URL
ARG VITE_API_VISIT_URL

# Set environment variables for Vite build
ENV VITE_API_AUTH_URL=$VITE_API_AUTH_URL
ENV VITE_API_CLIENT_URL=$VITE_API_CLIENT_URL
ENV VITE_API_VISIT_URL=$VITE_API_VISIT_URL

RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]
