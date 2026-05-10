# 📘 ERROR CODE DOCTRINE (ERP SYSTEM STANDARD)

## Version: 1.0
## Status: Active
## Scope: All ERP Modules (Person, Finance, Inventory, HR, Sales, Core)

---

# 1. PURPOSE

This document defines the standard rules for designing, assigning, and using error codes across the ERP system.

Error codes are a shared contract between:
- PostgreSQL database layer
- Backend services (Spring Boot)
- API layer (REST responses)
- Logs and monitoring systems

They ensure consistency, scalability, and predictable error handling.

---

# 2. CORE PRINCIPLE

Error codes represent machine-readable failure states, not human messages.

They exist to ensure:
- consistent error handling across modules
- predictable backend behavior
- clean separation between DB and backend logic
- improved debugging and observability

---

# 3. GLOBAL RULES

## 3.1 Single Source of Truth

All error codes MUST be defined in:

/contracts/error-codes/

No exceptions.

---

## 3.2 No Free-Text Database Errors

❌ Forbidden:
RAISE EXCEPTION 'Father not found';

✔ Required:
RAISE EXCEPTION USING ERRCODE = 'P1001';

---

## 3.3 Error Code Stability

Once defined, an error code:
- MUST NOT be reused for a different meaning
- MUST NOT be deleted (only deprecated)
- MUST remain stable across versions

---

## 3.4 Separation of Concerns

Database → emits error codes  
Backend → interprets error codes  
API → presents user-friendly messages  
Docs → defines meaning

---

# 4. ERROR CODE FORMAT STANDARD

## 4.1 Structure

[M][C][NNN]

Where:
- M = Module prefix
- C = Category
- NNN = sequential number

---

## 4.2 Example

P1001

---

# 5. MODULE PREFIXES

Each module MUST use a unique prefix:

P → Person  
F → Finance  
I → Inventory  
H → HR  
S → Sales  
A → Authentication  
C → Core/System  

---

# 6. ERROR CATEGORIES

## 6.1 Standard Categories

1xxx → Validation errors  
2xxx → Business rule violations  
3xxx → Integrity / relational DB violations  
4xxx → Security / authorization  
9xxx → System / unexpected errors  

---

## 6.2 Examples

P1001 → Validation error (Person module)  
P2001 → Business rule violation  
P3001 → Integrity violation  

---

# 7. DATABASE ERROR RULES (POSTGRESQL)

All PostgreSQL functions MUST:

- use structured error codes
- avoid free-text errors
- emit module-specific error codes

---

## 7.1 Required Format

RAISE EXCEPTION
    USING ERRCODE = 'P1001',
          MESSAGE = 'FATHER_NOT_FOUND';

---

## 7.2 Rules

- ERRCODE is mandatory
- MESSAGE is optional (debugging only)
- DB must NOT contain business formatting logic

---

# 8. BACKEND HANDLING RULES (SPRING BOOT)

Backend MUST:

- decode DB error codes centrally
- NOT scatter string parsing across services
- map error codes → domain exceptions

---

## 8.1 Flow

DB Error Code → Backend Decoder → Domain Exception → API Response

---

## 8.2 Example Mapping

P1001 → ParentNotFoundException  
P1002 → InvalidParentGenderException  
P2001 → PersonAlreadyExistsException  

---

# 9. MODULE OWNERSHIP RULE

Each module owns its error codes.

✔ Correct:
- Person module → Pxxxx
- Finance module → Fxxxx

❌ Wrong:
Finance using P1001

---

# 10. DEPRECATION RULE

If an error code becomes obsolete:
- it MUST NOT be deleted
- it MUST be marked as deprecated

Example:
P1001 → DEPRECATED (use P1050)

---

# 11. VERSIONING RULE

Error codes are backward compatible.

- Meaning MUST NOT change
- New meaning requires new code

---

# 12. DEBUGGING RULE

Logs MUST include:
- module
- error code
- context

Example:
[PERSON][P1001] Father not found

---

# 13. DESIGN PRINCIPLE SUMMARY

- Error codes = contract between DB and backend
- DB emits codes only
- Backend interprets codes
- API formats response
- Docs define meaning

---

# 14. GOLDEN RULE

If an error has business meaning → it MUST have a code.