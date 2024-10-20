# Use an official Python runtime as a parent image
FROM python:3.11-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install FastAPI and Uvicorn dependencies
RUN pip3 install --no-cache-dir fastapi uvicorn pydantic transformers torch

RUN pip3 install sentencepiece

# Expose the port that Uvicorn will run on
EXPOSE 8000

# Command to run the FastAPI app using Uvicorn
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]

# -p 127.0.0.1:80:8080/tcp