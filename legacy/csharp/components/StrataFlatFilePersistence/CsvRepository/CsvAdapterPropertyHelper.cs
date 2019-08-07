using System.Linq;

namespace Strata.FlatFilePersistence.CsvRepository
{
    public static class CsvAdapterPropertyHelper
    {
        public static TTo Copy<TFrom, TTo>(TFrom source, TTo target)
        {
            foreach (var sourceProperty in source.GetType().GetProperties()
                .Where(propertyInfo => propertyInfo.CanRead))
            {
                var targetProperty = target.GetType().UnderlyingSystemType.GetProperty(sourceProperty.Name);
                if (targetProperty.CanWrite)
                {
                    targetProperty.SetValue(target, sourceProperty.GetValue(source, null), null);
                }
            }

            return target;
        }
    }
}
