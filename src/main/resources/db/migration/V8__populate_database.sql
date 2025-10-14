INSERT INTO `categories` (`id`, `name`)
VALUES (1, 'Electronics'),
       (2, 'Books'),
       (3, 'Home & Kitchen'),
       (4, 'Clothing'),
       (5, 'Sports & Outdoors'),
       (6, 'Toys & Games'),
       (7, 'Health & Beauty'),
       (8, 'Groceries');

INSERT INTO `products` (`name`, `price`, `description`, `category_id`)
VALUES
-- Electronics
('ProBook X1 Laptop', 1299.99,
 'High-performance laptop with a 15.6-inch 4K display, 16GB RAM, 512GB SSD, and a powerful next-gen processor. Perfect for professionals and creatives.',
 1),
('Quantum Sound Headphones', 199.50,
 'Wireless over-ear headphones with active noise cancellation, 30-hour battery life, and crystal-clear audio. Includes a carrying case.',
 1),
('Galaxy Explorer Smartphone', 899.00,
 'The latest flagship smartphone featuring a stunning 6.7-inch OLED screen, a triple-camera system for professional-grade photos, and all-day battery life.',
 1),
('StreamView 4K Smart TV', 649.99,
 '55-inch Ultra HD Smart TV with HDR support, built-in streaming apps, and a sleek, minimalist design to fit any living room.',
 1),
('ChronoFit Smartwatch', 249.95,
 'A sleek smartwatch that tracks your fitness, monitors your heart rate, and delivers notifications straight to your wrist. Water-resistant up to 50 meters.',
 1),

-- Books
('The Last Voyager', 15.99,
 'A thrilling science fiction novel by author Jane Dane, exploring the mysteries of deep space and the human spirit.',
 2),
('Sapiens: A Brief History of Humankind', 22.50,
 'A critically acclaimed non-fiction book by Yuval Noah Harari that explores the entire history of the human species.',
 2),
('The Art of Simple Cooking', 35.00,
 'A comprehensive cookbook filled with over 100 recipes that focus on fresh ingredients and fundamental techniques.',
 2),

-- Home & Kitchen
('AeroPress Coffee Maker', 39.95,
 'A revolutionary coffee press that brews smooth, rich, and grit-free coffee in under a minute. Ideal for home and travel.',
 3),
('Velocity Pro Blender', 119.99,
 'A powerful 1200-watt blender designed to crush ice, blend smoothies, and puree soups with ease. Includes a 64 oz BPA-free pitcher.',
 3),
('CrispMaster Air Fryer', 89.99,
 'Enjoy your favorite fried foods with up to 75% less fat. This 5.8-quart air fryer is perfect for families and easy to clean.',
 3),

-- Clothing
('Classic Organic Cotton T-Shirt', 25.00,
 'A soft, durable, and eco-friendly t-shirt made from 100% organic cotton. Available in various colors.', 4),
('UrbanFlex Denim Jeans', 79.50,
 'Comfortable and stylish slim-fit jeans made with stretch denim for maximum mobility and a perfect fit.', 4),
('TrailWind Running Shoes', 129.99,
 'Lightweight and responsive running shoes with advanced cushioning and a breathable mesh upper for ultimate comfort on any terrain.',
 4),

-- Sports & Outdoors
('ZenFlow Yoga Mat', 69.99,
 'An extra-thick, non-slip yoga mat made from eco-friendly materials. Provides excellent grip and cushioning for your practice.',
 5),
('Adjustable Dumbbell Set (5-50 lbs)', 299.00,
 'A space-saving adjustable dumbbell set that replaces 15 sets of weights. Easily change your resistance with the turn of a dial.',
 5),

-- Toys & Games
('Settlers of Catan Board Game', 49.99,
 'An award-winning strategy board game where players collect resources and build settlements to dominate the island of Catan.',
 6),
('Cosmic Voyager LEGO Set', 149.99,
 'A challenging and detailed 1,500-piece LEGO set to build a futuristic spaceship, complete with minifigures and accessories.',
 6),

-- Health & Beauty
('SonicGlow Electric Toothbrush', 79.99,
 'An advanced electric toothbrush with 5 brushing modes, a pressure sensor, and a 2-minute timer to improve your oral health.',
 7),

-- Groceries
('Organic Colombian Coffee Beans', 18.99,
 'A 12 oz bag of single-origin, medium-roast whole bean coffee with notes of chocolate and citrus.', 8);