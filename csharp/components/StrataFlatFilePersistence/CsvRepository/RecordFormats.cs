using System;

namespace Strata.FlatFilePersistence.CsvRepository
{
    public static class FlatFileRecordFormats
    {
        public const string CharDelimited = "CharDelimited";
        public const string Rfc4180 = "Rfc4180";

        [Obsolete("Recommendation: Do not use.  What is the CG standard today?")]
        public const string CgStandard = "CgStandard";
    }
}
