ALTER TABLE "financial_records_categories"
    RENAME CONSTRAINT "fk_categories_transactions" TO "fk_categories_financial_records";

ALTER TABLE "financial_records_categories"
    RENAME CONSTRAINT "fk_transactions_categories" TO "fk_financial_records_categories";