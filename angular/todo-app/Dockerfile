# Use an official Node.js runtime as the base image
FROM node:18-alpine as build

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install the dependencies
RUN npm install
RUN npm install -g @angular/cli

# Copy the rest of the application code to the working directory
COPY . .

# Build the Angular app
RUN npm run build --configuration=production

# Use an official Nginx runtime as the base image for serving the Angular app
FROM nginx:latest

# Copy the built Angular app from the build stage to the Nginx web root
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/todo-app/browser /usr/share/nginx/html

# Expose the port that Nginx will run on
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]