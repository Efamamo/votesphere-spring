TARGET_DIR := target
JAR_NAME := $(shell ls $(TARGET_DIR)/*.jar 2>/dev/null | head -n 1)

.PHONY: build run test clean

# Build the project and create a JAR file
build:
	@mvn clean package
	@echo "Build completed successfully."

# Run the application using Maven
run:
	@mvn spring-boot:run
	@echo "Application is running..."

# Run tests
test:
	@mvn test
	@echo "Tests completed successfully."

# Clean the project
clean:
	@mvn clean
	@echo "Clean completed successfully."
