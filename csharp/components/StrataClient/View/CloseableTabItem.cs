//  ##########################################################################
//  # File Name: ClosableTabItem.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;

namespace Strata.Client.View
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class CloseableTabItem:
        TabItem
    {
        public String
        Title
        {
            set { ((CloseableHeader)Header).HeaderLabel.Content = value; }    
        }

        public 
        CloseableTabItem()
        {
            CloseableHeader header = new CloseableHeader();

            Header = header;

            header.CloseButton.MouseEnter += new MouseEventHandler(button_close_MouseEnter);
            header.CloseButton.MouseLeave += new MouseEventHandler(button_close_MouseLeave);
            header.CloseButton.Click += new RoutedEventHandler(button_close_Click);
            header.HeaderLabel.SizeChanged += new SizeChangedEventHandler(label_TabTitle_SizeChanged);
        }

        // Override OnSelected - Show the Close Button
         protected override void OnSelected(RoutedEventArgs e)
        {
            base.OnSelected(e);
            ((CloseableHeader)Header).CloseButton.Visibility = Visibility.Visible;
        }
   
         // Override OnUnSelected - Hide the Close Button
         protected override void OnUnselected(RoutedEventArgs e)
         {
             base.OnUnselected(e);
             ((CloseableHeader)Header).CloseButton.Visibility = Visibility.Hidden;
         }
     
         // Override OnMouseEnter - Show the Close Button
         protected override void OnMouseEnter(MouseEventArgs e)
         {
             base.OnMouseEnter(e);
             ((CloseableHeader)Header).CloseButton.Visibility = Visibility.Visible;
         }
     
         // Override OnMouseLeave - Hide the Close Button (If it is NOT selected)
         protected override void OnMouseLeave(MouseEventArgs e)
         {
             base.OnMouseLeave(e);
             if (!this.IsSelected)
             {
                 ((CloseableHeader)Header).CloseButton.Visibility = Visibility.Hidden;
             }
         }
   

         // Button MouseEnter - When the mouse is over the button - change color to Red
         void button_close_MouseEnter(object sender, MouseEventArgs e)
         {
             ((CloseableHeader)Header).CloseButton.Foreground = Brushes.Red;
         }
   
         // Button MouseLeave - When mouse is no longer over button - change color back to black
         void button_close_MouseLeave(object sender, MouseEventArgs e)
         {
             ((CloseableHeader)Header).CloseButton.Foreground = Brushes.Black;
         }
  
  
         // Button Close Click - Remove the Tab - (or raise an event indicating a "CloseTab" event has occurred)
         void button_close_Click(object sender, RoutedEventArgs e)
         {
             ((TabControl)this.Parent).Items.Remove(this);
         }
    
         // Label SizeChanged - When the Size of the Label changes (due to setting the Title) set position of button properly
         void label_TabTitle_SizeChanged(object sender, SizeChangedEventArgs e)
         {
             ((CloseableHeader)Header).CloseButton.Margin = new Thickness(((CloseableHeader)Header).HeaderLabel.ActualWidth + 5, 3, 4, 0);
         }
     
    }
}

//  ##########################################################################
