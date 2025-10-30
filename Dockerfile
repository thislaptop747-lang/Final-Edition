# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the jar file from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

4. Click **"Commit new file"**

---

### **Step 4: Create .dockerignore**

1. Click **"Add file"** → **"Create new file"**
2. **Name the file**: `.dockerignore` (starts with dot)
3. **Paste this content**:
```
target/
.mvn/
mvnw
mvnw.cmd
.git/
.gitignore
*.md
.idea/
*.iml
.DS_Store
*.log
```

4. Click **"Commit new file"**

---

### **Step 5: Create/Update .gitignore**

1. Click **"Add file"** → **"Create new file"**
2. **Name the file**: `.gitignore` (starts with dot)
3. **Paste this content**:
```
target/
!.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr

### VS Code ###
.vscode/

### Mac ###
.DS_Store

### Logs ###
*.log
