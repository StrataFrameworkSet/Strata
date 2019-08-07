using System.Configuration;
using Strata.Common.Cryptography;

namespace Strata.Common.Configuration {
    public static class ApplicationSettings {

        private static string _connectionString = string.Empty;

        public static string GetDecrpytedConnectionString(string connectionString)
        {
            if (!string.IsNullOrEmpty(connectionString))
            {
                int indexPassword = connectionString.IndexOf("password", System.StringComparison.CurrentCultureIgnoreCase);
                if (indexPassword > -1)
                {
                    int indexEqualSign = connectionString.IndexOf("=", indexPassword);
                    if (indexEqualSign > -1)
                    {
                        int indexSemiColon = connectionString.IndexOf(";", indexEqualSign);
                        if (indexSemiColon > -1)
                        {
                            string encryptedPassword = connectionString.Substring(indexEqualSign + 1,
                                                                                    indexSemiColon - indexEqualSign -
                                                                                    1);
                            string decryptedPassword = CryptoApi.DecryptPass(encryptedPassword.Trim());
                            string decryptedConnString = connectionString.Replace(encryptedPassword,
                                                                                    decryptedPassword);
                            _connectionString = decryptedConnString;
                        }
                    }
                }
            }

            return _connectionString;
        }
    
        private const string ENVIRONMENT_KEY = "Environment";
        private const string ENVIRONMENT_DEFAULT = "DEBUG";

        private static string _environment = string.Empty;

        ///<summary>
        /// The environment the application is executing in.  This is used in the construction of the service URLs.
        ///</summary>
        public static string Environment
        {
            get
            {
                if (string.IsNullOrEmpty(_environment))
                {
                    _environment = ConfigurationManager.AppSettings[ENVIRONMENT_KEY] ?? ENVIRONMENT_DEFAULT;
                }

                return _environment.ToLowerInvariant();
            }
        }
    }
}
