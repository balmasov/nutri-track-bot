SAVE_USER = INSERT INTO nutri_bot.users (id, name, step) VALUES (?, ?, ?);
UPDATE_USER = UPDATE nutri_bot.users SET name = ?, step = ? WHERE id = ?;
GET_USER = SELECT id, name, step FROM nutri_bot.users WHERE id = ?;

SAVE_FOOD = INSERT INTO nutri_bot.foods (cooked_weight, name, raw_weight, state, user_id) VALUES (?, ?, ?, ?, ?);
UPDATE_FOOD = UPDATE nutri_bot.foods SET cooked_weight = ?, name = ?, raw_weight = ?, state = ?, user_id = ? WHERE id = ?;
GET_COOKING_FOOD = SELECT id, cooked_weight, name, raw_weight, state, user_id FROM nutri_bot.foods WHERE state = 'COOKING' AND user_id = ?;
GET_CALCULATION_FOOD = SELECT id, cooked_weight, name, raw_weight, state, user_id FROM nutri_bot.foods WHERE state = 'CALCULATING' AND user_id = ?;
GET_FOOD_BY_ID_AND_NAME = SELECT id, cooked_weight, name, raw_weight, state, user_id FROM nutri_bot.foods WHERE user_Id = ? AND name = ?;
DELETE_PROCESSED_FOOD = DELETE FROM nutri_bot.foods WHERE user_id = ? AND state = 'COOKING';
GET_USER_COOKED_FOOD = SELECT id, name, cooked_weight, raw_weight FROM nutri_bot.foods WHERE user_id = ?;
GET_FOOD_BY_ID = SELECT id, cooked_weight, name, raw_weight, state, user_id FROM nutri_bot.foods WHERE id = ?;
GET_PROCESSED_FOOD_BY_USER_ID = SELECT id, cooked_weight, name, raw_weight, state, user_id FROM nutri_bot.foods WHERE user_id = ? and state != 'COOKED';
GET_ALL_USERS_FOOD = SELECT id, cooked_weight, raw_weight, name, user_id FROM nutri_bot.foods WHERE user_id = ?;
DELETE_FOOD_BY_ID = DELETE FROM nutri_bot.foods WHERE id = ? RETURNING name;
DELETE_USER_FOOD = DELETE FROM nutri_bot.foods WHERE user_id = ?;