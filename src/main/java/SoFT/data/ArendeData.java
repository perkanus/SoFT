package SoFT.data;

public class ArendeData {

    public String Personnummer;
    public String Kundnummer = "";
    public String skapaFTJTILL = "N";
    public String skapaSJRTILL = "N";
    public String resenarSkapad = "N";
    public String FTJArendeId = "";
    public String SJRArendeId = "";
    public String tillstandFTJId = "";
    public String tillstandSJRId = "";
    public String kortnummerFTJ = "";
    public String kortnummerSJR = "";



    // Tilldelningsfält
    //
    public String tilldeningFardsatt;
    public String tilldeningAntalLedsagare;
    public String tilldeningElmoped;
    public String tilldeningEnsamakning;
    public String tilldeningFramsatesplacering;
    public String tilldeningAllergianpassat;
    public String tilldeningHund;
    public String tilldeningLansgransdispens;
    public String tilldeningBattaxi;

    // Beslutsfält
    //
    public String beslutsText;
    public String beslutatKod;
    public String idemiaData;

    public ArendeData(
            String pnr,
            String flagftjtill,
            String flagsjrtill,
            String flagisadded,
            String ftjArrId,
            String custId
    )
    {

        this.Personnummer = pnr;
        this.Kundnummer = custId;
        this.skapaFTJTILL = flagftjtill;
        this.skapaSJRTILL = flagsjrtill;
        this.resenarSkapad = flagisadded;
        this.FTJArendeId = ftjArrId;


        // Tilldelningsfält
        //
        tilldeningFardsatt = "";
        tilldeningAntalLedsagare = "";
        tilldeningElmoped = "";
        tilldeningEnsamakning = "";
        tilldeningFramsatesplacering = "";
        tilldeningAllergianpassat = "";
        tilldeningHund ="";
        tilldeningLansgransdispens = "";
        tilldeningBattaxi = "";
    }


    public ArendeData(                             // Excel Cell index
                                                   String Personnummer,                // 0
                                                   String skapaFTJTILL,                // 1
                                                   String skapaSJRTILL,                // 2
                                                   String resenarSkapad,               // 3
                                                   String FTJArendeId,                 // 4
                                                   String SJRArendeId,                 // 5
                                                   String Kundnummer,                  // 6
                                                   String tillstandFTJId,              // 7
                                                   String tillstandSJRId,              // 8
                                                   String kortnummerFTJ,               // 9
                                                   String kortnummerSJR,               // 10
                                                   String tilldeningFardsatt,          // 11
                                                   String tilldeningAntalLedsagare,    // 12
                                                   String tilldeningElmoped,           // 13
                                                   String tilldeningEnsamakning,       // 14
                                                   String tilldeningFramsatesplacering,// 15
                                                   String tilldeningAllergianpassat,   // 16
                                                   String tilldeningHund,              // 17
                                                   String tilldeningLansgransdispens,  // 18
                                                   String tilldeningBattaxi,           // 19
                                                   String beslutsText,                 // 20
                                                   String beslutatKod,                 // 21
                                                   String idemiaData                   // 22

    )
    {

        this.Personnummer = Personnummer;
        this.Kundnummer = Kundnummer;
        this.skapaFTJTILL = skapaFTJTILL;
        this.skapaSJRTILL = skapaSJRTILL;
        this.resenarSkapad = resenarSkapad;
        this.FTJArendeId = FTJArendeId;
        this.SJRArendeId = SJRArendeId;
        this.tillstandFTJId = tillstandFTJId;
        this.tillstandSJRId = tillstandSJRId;
        this.kortnummerFTJ = (kortnummerFTJ != null) ? kortnummerFTJ : "";
        this.kortnummerSJR = (kortnummerSJR != null) ? kortnummerSJR : "";



        // Tilldelningsfält
        //
        this.tilldeningFardsatt = tilldeningFardsatt;
        this.tilldeningAntalLedsagare = (tilldeningAntalLedsagare != null) ? tilldeningAntalLedsagare : "";
        this.tilldeningElmoped = (tilldeningElmoped != null) ? tilldeningElmoped : "";
        this.tilldeningEnsamakning = (tilldeningEnsamakning != null) ? tilldeningEnsamakning : "";
        this.tilldeningFramsatesplacering = (tilldeningFramsatesplacering != null) ? tilldeningFramsatesplacering : "";
        this.tilldeningAllergianpassat = (tilldeningAllergianpassat != null) ? tilldeningAllergianpassat : "";
        this.tilldeningHund = (tilldeningHund != null) ? tilldeningHund : "";
        this.tilldeningLansgransdispens = (tilldeningLansgransdispens != null) ? tilldeningLansgransdispens : "";
        this.tilldeningBattaxi = (tilldeningBattaxi != null) ? tilldeningBattaxi : "";

        // Beslutsfält
        //
        this.beslutsText = beslutsText;
        this.beslutatKod = beslutatKod;
        this.idemiaData = (idemiaData != null) ? idemiaData : "";
    }
    // TODO: Add multiple classes to keep data saved
    //
    public class ResenarData
    {

    }
}
