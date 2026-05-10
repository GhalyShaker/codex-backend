# Database Error Codes (Civil Registry)

This document defines custom PostgreSQL error codes used in the Civil Registry database.

All errors are raised using:

```sql
RAISE EXCEPTION ... USING ERRCODE = 'PXXXX';