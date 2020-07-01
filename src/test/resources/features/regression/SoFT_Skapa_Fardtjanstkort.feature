# language: sv
Feature: Arendehantering

  # 001 - Skapa Färdtjänstkort Fotodispens: Ja,  Tidsbegränsning: Nej
  # 002 - Skapa Färdtjänstkort Fotodispens: Nej, Tidsbegränsning: Nej, Gammalt Kort: Ja
  #
  #



  # 001 - Skapa Färdtjänstkort Fotodispens: Ja, Tidsbegränsning: Nej
  Scenario: Create a Arende_Fardtjanstkort 001
    Given user is logged in in SoFT
    When user selects the arrow next to Arendehantering in SoFT Ribbon
    And user selects Resenarer
    Then Resenar list opens


    # Välj användare och

    When user selects a Resenar
    And user verifies Resenar is correct
    And user expands Arende
    And user selects to create a new Fardtjanstarende
    And user selects Ansokan_fardtjanst in Ansokningstyp
    Then Arendetyp defaults to Nytt_Tillstand

    # Spara. kolla syntax om And är tillåtet efter Then

    When user selects SPARA button
    Then the Fardtjanstarende is saved

    # OPTION: Sätt mig som handläggare Ja
    Then user set Satt_Mig_Som_Handlaggare to "Ja"

    # OPTION: Sätt Giltigt till till kortare än 3 månader
    When user sets Giltigt_Till to shorter than 90 days from Giltigt_Fran
    Then a notification with text "Period för tillstånd är kortare än 3 månader"

    # OPTION: Sätt Giltigt till till ett datum före Giltigt från
    When user sets Giltigt_Till to a date preceding Giltigt_Fran
    Then a notification with text "Tillståndets giltigt till måste inträffa efter giltigt från"

    # OPTION: Välj Tidsbegränsning
    Then user set Satt_Mig_Som_Handlaggare to "Ja"












	# Fotodispens är satt till Ja
    And user sets Fotodispens to Ja
    And user sets Fotodispens_Giltigt_Till to a date in the future

    # Registreringsorsak
    And user enters Registreringsorsak

	# Tidsbegränsning: Ingen tidsbegränsning
    And user enters Tidsbegransning

    # Automatisk validering i systemet
    When user saves Arende_Fardtjanstkort
    Then SoFT validates that no duplicate draft for Arende_Fardtjanstkort exists
    And SoFT validates that Farbevistyp is entered
    And SoFT validates that Resenar is entered
    And SoFT validates that Resenar status is Aktiv
    And SoFT validates that Registreringsorsak is selected
    And SoFT validates that Fotodispens is entered with value Ja
    And SoFT validates that Fotodispens_Giltigt_Till is set to a date in the future
    And SoFT validates that Fardbeviset_Giltig_Till is set to todays date or a date in the future
	# Kontrollera roller. Skrivs om.
    And SoFT validates that user or user_team has at least one role in

    And SoFT validates that Fardbeviset gets a unique Id_Nummer
    And SoFT validates that Fardbeviset_Giltig_Fran is set to todays date
    And SoFT validates that Fardbeviset_Namn is set according to Namnstandard
    And SoFT validates that Fardbeviset_Status is set to Utkast
    And SoFT validates that Fardbeviset gets an Fardbeviset_Identitetssok_Referens

