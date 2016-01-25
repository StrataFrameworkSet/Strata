using System;
using Strata.Common.MoneyDomain;
using Strata.FlatFilePersistence.CsvRepository;
using Strata.Persistence.TradeDomain;
using CapitalGroup.Common.RecordStreamer;

namespace Strata.FlatFilePersistence
{
    public class CsvTradeAdapter : Trade
    {
        private enum TradeFieldOrder
        {
            TradeKey,
            TradeId,
            BrokerCode,
            CouponRate,
            Cusip,
            DeliveryDate,
            ExternalTradeId,
            PriceAmount,
            PriceCurrency,
            TradeAmountValue,
            TradeAmountCurrency,
            SettlementDate,
            TradeDate,
            TraderId,
            TransactionType,
            Version
        }

        public CsvTradeAdapter(): base() {}
        public CsvTradeAdapter(Trade other) : base(other) {}

        [Mapping((int)TradeFieldOrder.TradeKey)]
        public override long TradeKey
        {
            get { return base.TradeKey; }
            set { base.TradeKey = value; }
        }

        [Mapping((int)TradeFieldOrder.TradeId)]
        public override int TradeId
        {
            get { return base.TradeId; }
            set { base.TradeId = value; }
        }

        [Mapping((int)TradeFieldOrder.BrokerCode)]
        public override string BrokerCode
        {
            get { return base.BrokerCode; }
            set { base.BrokerCode = value; }
        }

        [Mapping((int)TradeFieldOrder.CouponRate)]
        public override decimal CouponRate
        {
            get { return base.CouponRate; }
            set { base.CouponRate = value; }
        }

        [Mapping((int)TradeFieldOrder.Cusip)]
        public override string Cusip
        {
            get { return base.Cusip; }
            set { base.Cusip = value; }
        }

        [Mapping((int)TradeFieldOrder.DeliveryDate)]
        public override DateTime DeliveryDate
        {
            get { return base.DeliveryDate; }
            set { base.DeliveryDate = value; }
        }

        [Mapping((int)TradeFieldOrder.ExternalTradeId)]
        public override int ExternalTradeId
        {
            get { return base.ExternalTradeId; }
            set { base.ExternalTradeId = value; }
        }

        [Mapping((int)TradeFieldOrder.PriceAmount)]
        public decimal PriceAmount
        {
            get { return base.Price.Amount; }
            set { base.Price = new Money(base.Price.Currency, value); }
        }

        [Mapping((int)TradeFieldOrder.PriceCurrency)]
        public string PriceCurrency
        {
            get { return base.Price.Currency.CurrencyCode; }
            set { base.Price = new Money(CurrencyManager.GetInstanceByCode(value), base.Price.Amount); }
        }

        [Mapping((int)TradeFieldOrder.SettlementDate)]
        public override DateTime SettlementDate
        {
            get { return base.SettlementDate; }
            set { base.SettlementDate = value; }
        }

        [Mapping((int)TradeFieldOrder.TradeAmountValue)]
        public decimal TradeAmountValue
        {
            get { return base.TradeAmount.Amount; }
            set { base.TradeAmount = new Money(base.TradeAmount.Currency, value); }
        }

        [Mapping((int)TradeFieldOrder.TradeAmountCurrency)]
        public string TradeAmountCurrency
        {
            get { return base.TradeAmount.Currency.CurrencyCode; }
            set { base.TradeAmount = new Money(CurrencyManager.GetInstanceByCode(value), base.TradeAmount.Amount); }
        }

        [Mapping((int)TradeFieldOrder.TradeDate)]
        public override DateTime TradeDate
        {
            get { return base.TradeDate; }
            set { base.TradeDate = value; }
        }

        [Mapping((int)TradeFieldOrder.TraderId)]
        public override int TraderId
        {
            get { return base.TraderId; }
            set { base.TraderId = value; }
        }

        [Mapping((int)TradeFieldOrder.TransactionType)]
        public string TransactionTypeString
        {
            get { return base.TransactionType.ToString(); }
            set { base.TransactionType = (TransactionType)Enum.Parse(typeof(TransactionType), value); }
        }

        [Mapping((int)TradeFieldOrder.Version)]
        public override int Version
        {
            get { return base.Version; }
            set { base.Version = value; }
        }

        public static long GetKey(CsvTradeAdapter trade)
        {
            return trade.TradeKey;
        }
    }
}
