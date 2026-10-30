# Online Shopping Cart with Discount Strategies

Java coding challenge: Discount Strategies, Java candidate pack.

| Attribute   | Detail                                                       |
|-------------|--------------------------------------------------------------|
| Difficulty  | Medium                                                       |
| Duration    | 60–75 minutes                                                |
| Language    | Java 17                                                      |
| Testing     | JUnit 4.13.2                                                 |
| Build       | Maven and Gradle                                             |
| Focus       | Generics, polymorphism, Strategy pattern, validation         |
| Deliverable | Complete strategies, product overrides, cart totals and summary |

> **Assessment note:** Starter methods are intentionally incomplete. Preserve all names specified in the problem statement.

## 1. Problem Statement

Implement an extensible shopping cart that applies product-specific discounts through generic strategies and runtime polymorphism. Electronics receive 10%, clothing 20%, and groceries 30%. Customers can add heterogeneous products, replace a product strategy dynamically, calculate totals, and obtain a checkout summary.

## 2. Clarified Contract

- `DiscountStrategy<T>.calculateDiscount(T type)` returns the discount amount.
- `Product.calculateDiscount()` is abstract and each subclass delegates to its typed strategy.
- `getDiscountedPrice()` equals price minus discount amount.
- Category values are fixed by subclass: `Electronics`, `Clothing`, `Groceries`.
- Prices must be non-negative; zero-priced products are valid.
- Product IDs are trimmed and unique ignoring case within a cart.
- The cart preserves insertion order and exposes an unmodifiable product list.
- Dynamic strategies are supported through typed `setDiscountStrategy` methods.
- `checkout()` rejects an empty cart and returns immutable summary lines.
- `displayCheckoutSummary()` uses two decimal places and deterministic totals.

## 3. Candidate Instructions

- Use the exact class, method and variable names from the supplied starter code.
- Do not replace generic strategies with category switch statements.
- Do not remove subclass `calculateDiscount` overrides.
- Do not change or weaken supplied tests.
- Be prepared to explain compile-time type safety and runtime dispatch.

## Project Layout

```
README.md
build.gradle
pom.xml
settings.gradle
src/main/java/com/company/cart/CheckoutSummary.java
src/main/java/com/company/cart/ClothingDiscountStrategy.java
src/main/java/com/company/cart/ClothingProduct.java
src/main/java/com/company/cart/DiscountStrategy.java
src/main/java/com/company/cart/ElectronicsDiscountStrategy.java
src/main/java/com/company/cart/ElectronicsProduct.java
src/main/java/com/company/cart/GroceriesDiscountStrategy.java
src/main/java/com/company/cart/GroceriesProduct.java
src/main/java/com/company/cart/Main.java
src/main/java/com/company/cart/Product.java
src/main/java/com/company/cart/ShoppingCart.java
src/test/java/com/company/cart/DiscountCartTest.java
```

## Build and Run

Requires JDK 17.

| Purpose        | Maven                                                  | Gradle                                |
|----------------|--------------------------------------------------------|---------------------------------------|
| Compile        | `mvn clean compile`                                    | `gradle clean compileJava`            |
| Run all tests  | `mvn clean test`                                       | `gradle clean test`                   |
| Run test class | `mvn -Dtest=DiscountCartTest test`                     | `gradle test --tests DiscountCartTest`|
| Run Main       | `mvn exec:java -Dexec.mainClass=com.company.cart.Main` | `gradle run`                          |

### Expected output of `Main`

```
Checkout Summary
E1 | Laptop | Electronics | Price: 1000.00 | Discount: 100.00 | Final: 900.00
C1 | Jacket | Clothing | Price: 200.00 | Discount: 40.00 | Final: 160.00
G1 | Rice | Groceries | Price: 100.00 | Discount: 30.00 | Final: 70.00
Subtotal: 1300.00
Total Discount: 170.00
Final Total: 1130.00
```

## Solution Notes

- **Generics (compile-time type safety).** Each strategy implements `DiscountStrategy<ConcreteProduct>`, and each product holds a `DiscountStrategy<ItsOwnType>`. Passing an `ElectronicsDiscountStrategy` to a `ClothingProduct` will not compile, so no casts are needed anywhere.
- **Polymorphism (runtime dispatch).** `ShoppingCart` only knows `Product`. Calling `product.calculateDiscount()` dispatches at runtime to the subclass override, which delegates to `discountStrategy.calculateDiscount(this)`.
- **Strategy pattern.** Rates live in the strategy classes (10% / 20% / 30%). `setDiscountStrategy(...)` swaps the algorithm at runtime (for example, `p.setDiscountStrategy(x -> 50)`) and rejects `null` with a `NullPointerException`.
- **Validation in `addProduct`:**
  - `null` product → `NullPointerException`
  - blank or `null` id, name or category → `IllegalArgumentException`
  - negative, `NaN` or infinite price → `IllegalArgumentException`
  - duplicate id (trimmed, ignoring case) → `IllegalStateException`

  The trimmed id is stored on the product.
- **Deterministic summary.** Formatting uses `Locale.ROOT` and `\n`, so the text is identical on every OS and locale (for example, `1300.00`, never `1300,00`).

## Part V: Evaluation

### Expected Completion-Time Guidelines

| Activity            | Time      | Focus                              |
|---------------------|-----------|------------------------------------|
| Understand design   | 10 min    | Generic contract and polymorphism  |
| Strategies/products | 15–20 min | Rates and typed delegation         |
| Shopping cart       | 20–25 min | Validation, totals and summary     |
| Test/review         | 15–20 min | Focused and complete suite         |
| Total               | 60–75 min | Complete tested solution           |

### Evaluation Rubric

| Area                   | Weight | Expected Evidence                                       |
|------------------------|--------|---------------------------------------------------------|
| Functional correctness | 35%    | Rates, totals and checkout are correct.                 |
| Generics and strategy  | 25%    | Typed reusable strategies without unsafe casts.         |
| Polymorphism           | 20%    | Overridden behavior works through Product references.   |
| Validation/edge cases  | 10%    | Invalid and boundary cases are handled.                 |
| Code quality/tests     | 10%    | Readable extensible code and unchanged passing tests.   |

### Submission Checklist

- [x] Maven and Gradle compile.
- [x] All JUnit 4 tests pass (19/19 on Maven 3.10 and Gradle 9.8 with JDK 17).
- [x] Exact required names are preserved.
- [x] All rates are correct.
- [x] Dynamic strategy replacement works.
- [x] Cart totals and summary are correct.
- [x] No supplied test was changed.

## Source Note

The Strategy pattern defines and encapsulates a family of interchangeable algorithms so behavior can vary independently of the client. Java method overriding provides runtime polymorphism, while generics provide compile-time type safety for reusable strategy implementations.
