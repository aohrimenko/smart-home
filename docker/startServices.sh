#!/bin/bash

echo "🚀 Starting Smart Home services with Docker Compose..."
if docker compose -f docker-compose.yml up -d; then
  echo "Services started successfully!"
else
  echo "Failed to start services."
  exit 1
fi