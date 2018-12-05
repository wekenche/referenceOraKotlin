ALTER TABLE `messages`
	ADD CONSTRAINT `messages_accounts_id_fk` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON UPDATE CASCADE ON DELETE CASCADE;