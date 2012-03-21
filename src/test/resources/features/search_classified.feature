# language: en
@done
Feature: : A customer can search classifieds by criteria
  As a customer
  I want to search classifieds by criteria

  Background:
    Given the repository:
      | title                            | description                                                                                                                                                                                                                                    | price    | reference | email                  | location.streetAddress         | location.city      | location.postalCode | location.countryCode |
      | Vente Appartement 4 pièces       | Comme neuf !! Ce grand 4 pièces, situé dans une rue calme, proche de la défense, vient d'être refait a neuf. Cuisine équipée, terrasse, WC séparés                                                                                             | 394000.0 | REF-00001 | foo@bar.com            | 5 rue Marcel Sembat            | Courbevoie         | 92400               | fr                   |
      | Chef de projet marketing web h/f | Sous l’autorité du responsable marketing France, vous serez en charge de la définition d'actions marketing, de leur mise en œuvre, ainsi que de leur suivi. Votre objectif sera de développer le trafic et les ventes de nos sites partenaires | 70000.0  | REF-00002 | apply@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |

  @done
  @#3

  Scenario: A customer can search classifieds by status (exact match)
    Given I am a customer
    When I search classifieds for which "status" is "draft"
    Then I get "2" items
    And the items are:
      | title                            | price    | reference | email                  | location.streetAddress         | location.city      | location.postalCode | location.countryCode |
      | Chef de projet marketing web h/f | 70000.0  | REF-00002 | apply@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Vente Appartement 4 pièces       | 394000.0 | REF-00001 | foo@bar.com            | 5 rue Marcel Sembat            | Courbevoie         | 92400               | fr                   |

  @done
  @#4

  Scenario: A customer can search classifieds by reference (exact match)
    Given I am a customer
    When I search classifieds for which "reference" is "REF-00002"
    Then I get "1" items
    And the items are:
      | title                            | price   | reference | email                  | location.streetAddress         | location.city      | location.postalCode | location.countryCode |
      | Chef de projet marketing web h/f | 70000.0 | REF-00002 | apply@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |

  @done
  @#5

  Scenario: A customer can search classifieds by email (exact match)
    Given I am a customer
    When I search classifieds for which "email" is "apply@vente-privee.com"
    Then I get "1" items
    And the items are:
      | title                            | price   | reference | email                  | location.streetAddress         | location.city      | location.postalCode | location.countryCode |
      | Chef de projet marketing web h/f | 70000.0 | REF-00002 | apply@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |

  @done
  @#6

  Scenario: A customer can search classifieds by country code (exact match)
    Given I am a customer
    When I search classifieds for which "location.countryCode" is "fr"
    Then I get "2" items
    And the items are:
      | title                            | price    | reference | email                  | location.streetAddress         | location.city      | location.postalCode | location.countryCode |
      | Chef de projet marketing web h/f | 70000.0  | REF-00002 | apply@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |
      | Vente Appartement 4 pièces       | 394000.0 | REF-00001 | foo@bar.com            | 5 rue Marcel Sembat            | Courbevoie         | 92400               | fr                   |

  @done
  @#7

  Scenario: A customer can search classifieds by postal code (full text)
    Given I am a customer
    When I search classifieds for which "location.postalCode" contains "93"
    Then I get "1" items
    And the items are:
      | title                            | price   | reference | email                  | location.streetAddress         | location.city      | location.postalCode | location.countryCode |
      | Chef de projet marketing web h/f | 70000.0 | REF-00002 | apply@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |

  @done
  @#8

  Scenario: A customer can search classifieds by location.city (full text)
    Given I am a customer
    When I search classifieds for which "location.city" contains "plaine"
    Then I get "1" items
    And the items are:
      | title                            | price   | reference | email                  | location.streetAddress         | location.city      | location.postalCode | location.countryCode |
      | Chef de projet marketing web h/f | 70000.0 | REF-00002 | apply@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |

  @done
  @#9

  Scenario: A customer can search classifieds by street address (full text)
    Given I am a customer
    When I search classifieds for which "location.streetAddress" contains "wils"
    Then I get "1" items
    And the items are:
      | title                            | price   | reference | email                  | location.streetAddress         | location.city      | location.postalCode | location.countryCode |
      | Chef de projet marketing web h/f | 70000.0 | REF-00002 | apply@vente-privee.com | 249 Avenue du Président Wilson | La Plaine St Denis | 93210               | fr                   |

  @done
  @#10

  Scenario: A customer can search classifieds by description (full text)
    Given I am a customer
    When I search classifieds for which "description" contains "défens"
    Then I get "1" items
    And the items are:
      | title                      | price    | reference | email       | location.streetAddress | location.city | location.postalCode | location.countryCode |
      | Vente Appartement 4 pièces | 394000.0 | REF-00001 | foo@bar.com | 5 rue Marcel Sembat    | Courbevoie    | 92400               | fr                   |

  @done
  @#11

  Scenario: A customer can search classifieds by title (full text)
    Given I am a customer
    When I search classifieds for which "title" contains "pièces"
    Then I get "1" items
    And the items are:
      | title                      | price    | reference | email       | location.streetAddress | location.city | location.postalCode | location.countryCode |
      | Vente Appartement 4 pièces | 394000.0 | REF-00001 | foo@bar.com | 5 rue Marcel Sembat    | Courbevoie    | 92400               | fr                   |
