package com.seidu;

import java.math.BigDecimal;

public enum PercentageReduction {

    TEN(new BigDecimal(0.1)) , TWENTY(new BigDecimal(0.2)) ,THIRTY(new BigDecimal(0.3)) ,
    FORTY(new BigDecimal(0.4)),FIFTY(new BigDecimal(0.5)),SIXTY(new BigDecimal(0.6)),
    SEVENTY(new BigDecimal(0.7)),EIGHTY(new BigDecimal(0.8)) , NINETY(new BigDecimal(0.9) );

    private BigDecimal reduceBy;

    PercentageReduction(BigDecimal reduceBy) {
        this.reduceBy = reduceBy;
    }

    public BigDecimal getReduceBy() {
        return reduceBy;
    }
}
