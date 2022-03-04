# Stage 1: Compile and Build angular codebase

# Use official node image as the base image
FROM node:16-alpine as build

# Set the working directory
WORKDIR /usr/local/app

# Add the source code to app
COPY ./angular-brainfog /usr/local/app/

# Install all the dependencies
RUN npm install

# Generate the build of the application
# Resolve the build configuration
ARG brainfog-build-config=production
RUN npm run build --configuration=${brainfog-build-config}


# Stage 2: Serve app with nginx server

# Use official nginx image as the base image
FROM nginx:latest

# Copy the build output to replace the default nginx contents.
COPY --from=build /usr/local/app/dist/angular-brainfog /usr/share/nginx/html

# Expose port 80
EXPOSE 80