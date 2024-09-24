# Use an official Node.js runtime as the base image for building the Angular app
FROM node:18-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install the dependencies
RUN npm install

# Copy the rest of the application code to the working directory
COPY . .

# Build the Angular app
RUN npm run build

# Use an official Nginx runtime as the base image for serving the Angular app
FROM nginx:alpine

# Copy the built Angular app from the build stage to the Nginx web root
COPY --from=build /app/dist/todo-app/browser /usr/share/nginx/html

# Copy the custom Nginx configuration file
COPY nginx.conf /etc/nginx/nginx.conf

# Expose the port that Nginx will run on
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]