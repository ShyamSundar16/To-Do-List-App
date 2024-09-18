# Use an official Node.js runtime as the base image
FROM node:18-alpine

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

# Expose the port that the app will run on
EXPOSE 80

# Define the command to run the app
CMD ["npm", "start"]