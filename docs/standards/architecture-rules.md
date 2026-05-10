# Architecture Rules (Temporary Stabilization Rules)

## 1. Module Ownership Rule
Each domain concept MUST belong to ONE module only.

- identity → owns User / Person identity
- auth → authentication only
- security → authorization only
- civil → civil records only

---

## 2. No Cross-Module Entity Creation
A module MUST NOT create or redefine entities owned by another module.

Example:
- auth MUST NOT define User entity
- security MUST NOT define User entity

---

## 3. Dependency Direction Rule
Allowed dependencies:

civil → identity  
auth → identity  
security → identity  

Forbidden reverse dependencies:
identity → civil ❌  
auth → civil ❌  
security → civil ❌  

---

## 4. One Source of Truth Rule
Each business concept MUST exist in only one module.

---

## 5. Temporary Rule (During Refactor)
If unsure where code belongs → put it in identity module temporarily.