# Use an official Node.js runtime as the base image
FROM node:latest as build

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install the dependencies
RUN npm ci
RUN npm install -g @angular/cli

# Copy the rest of the application code to the working directory
COPY . .

# Build the Angular app
RUN npm run build --configuration=production

FROM nginx:latest

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist/todo-app/browser /usr/share/nginx/html
# Expose the port that the app will run on
EXPOSE 80

# Define the command to run the app
#CMD ["ng", "serve"]