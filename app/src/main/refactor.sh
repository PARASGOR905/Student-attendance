#!/bin/bash

# Create new directory structure
mkdir -p app/src/main/java/com/example/student/{data,di,repository,ui,util,viewmodel}

# Move files from old package to new package
mv app/src/main/java/com/example/attendancetracker/* app/src/main/java/com/example/student/

# Remove old package directory
rm -r app/src/main/java/com/example/attendancetracker

# Update package declarations in Kotlin/Java files
find app/src/main/java/com/example/student -type f \( -name "*.kt" -o -name "*.java" \) -exec sed -i 's/package com\.example\.attendancetracker/package com.example.student/g' {} \;

# Update import statements
find app/src/main/java/com/example/student -type f \( -name "*.kt" -o -name "*.java" \) -exec sed -i 's/import com\.example\.attendancetracker/import com.example.student/g' {} \;

# Update any fully qualified names in XML files
find app/src/main/res -type f -name "*.xml" -exec sed -i 's/com\\.example\\.attendancetracker/com.example.student/g' {} \;

echo "Package refactoring complete. Please rebuild the project."