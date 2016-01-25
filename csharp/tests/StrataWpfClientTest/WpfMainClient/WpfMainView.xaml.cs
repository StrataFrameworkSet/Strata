//  ##########################################################################
//  # File Name: WpfMainView.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Controls.Ribbon;
using System.Windows.Documents;
using System.Windows.Media;
using Strata.Client.MainClient;

namespace Strata.WpfClient.WpfMainClient
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public partial 
    class WpfMainView: 
        RibbonWindow,
        IMainView
    {
        public IMainViewModel
        ViewModel
        {
            get { return ViewModel; }
            set
            {
                viewModel = value; 
                DataContext = viewModel;
            }       
        }

        private IMainViewModel viewModel;

        public
        WpfMainView()
        {
            viewModel = null;
            InitializeComponent();
            Loaded += WindowLoaded;
            InitialRows();
        }

        protected void 
        WindowLoaded(Object sender,RoutedEventArgs e)
        {
            ReplaceRibbonApplicationMenuButtonContent( AppMenu );
        }

        protected void 
        ReplaceRibbonApplicationMenuButtonContent(RibbonApplicationMenu menu)
        {
            Grid outerGrid = (Grid)VisualTreeHelper.GetChild(menu, 0);
            RibbonToggleButton toggleButton = (RibbonToggleButton)outerGrid.Children[0];
            ReplaceRibbonToggleButtonContent(toggleButton, menu.Label);

            Popup popup = (Popup)outerGrid.Children[2];
            EventHandler popupOpenedHandler = null;
            popupOpenedHandler = new EventHandler(delegate
            {
                Decorator decorator = (Decorator)popup.Child;
                Grid popupGrid = (Grid)decorator.Child;
                Canvas canvas = (Canvas)popupGrid.Children[1];
                RibbonToggleButton popupToggleButton = (RibbonToggleButton)canvas.Children[0];
                ReplaceRibbonToggleButtonContent(popupToggleButton, menu.Label);
                popup.Opened -= popupOpenedHandler;
            });
            popup.Opened += popupOpenedHandler;
        }

        protected void 
        ReplaceRibbonToggleButtonContent(RibbonToggleButton toggleButton, string text)
        {
            // Subdues the aero highlighting to that the text has better contrast.
            Grid grid = (Grid)VisualTreeHelper.GetChild(toggleButton, 0);
            Border middleBorder = (Border)grid.Children[1];
            middleBorder.Opacity = .5;

            // Replaces the images with the label text.
            StackPanel stackPanel = (StackPanel)grid.Children[2];
            UIElementCollection children = stackPanel.Children;
            children.RemoveRange(0, children.Count);
            TextBlock textBlock = new TextBlock(new Run(text));
            textBlock.Foreground = Brushes.White;
            children.Add(textBlock);
        }

        protected void
        InitialRows()
        {
            AppGrid
                .Items
                .Add( new DataRow() ); 
            AppGrid
                .Items
                .Add( new DataRow() ); 
            AppGrid
                .Items
                .Add( new DataRow() ); 
            AppGrid
                .Items
                .Add( new DataRow() ); 
            AppGrid
                .Items
                .Add( new DataRow() ); 
        }
    }
}
