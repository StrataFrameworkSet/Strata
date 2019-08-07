USE [XwingAutomatedTest]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FKC4C99F02E8D81083]') AND parent_object_id = OBJECT_ID(N'[dbo].[ManagerAllocation]'))
ALTER TABLE [dbo].[ManagerAllocation] DROP CONSTRAINT [FKC4C99F02E8D81083]
GO

USE [XwingAutomatedTest]
GO

/****** Object:  Table [dbo].[ManagerAllocation]    Script Date: 03/25/2013 08:35:01 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ManagerAllocation]') AND type in (N'U'))
DROP TABLE [dbo].[ManagerAllocation]
GO

USE [XwingAutomatedTest]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK93DEB807F80DB01F]') AND parent_object_id = OBJECT_ID(N'[dbo].[AccountAllocation]'))
ALTER TABLE [dbo].[AccountAllocation] DROP CONSTRAINT [FK93DEB807F80DB01F]
GO

USE [XwingAutomatedTest]
GO

/****** Object:  Table [dbo].[AccountAllocation]    Script Date: 03/25/2013 08:33:21 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AccountAllocation]') AND type in (N'U'))
DROP TABLE [dbo].[AccountAllocation]
GO

USE [XwingAutomatedTest]
GO

/****** Object:  Table [dbo].[Trade]    Script Date: 03/25/2013 08:35:17 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Trade]') AND type in (N'U'))
DROP TABLE [dbo].[Trade]
GO

