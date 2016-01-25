using Capgroup.Xwing.FlatFilePersistence.CsvRepository;

namespace Capgroup.Xwing.FlatFilePersistence
{
    public class CsvTradeFileConfiguration : CsvFileConfiguration 
    {
        public override string DataFileName
        {
            get { return "trades.csv"; }
        }

        public override string Path
        {
            get { return string.Empty; }
        }
    }
}
