import Joi from "joi";
import jwt from "jsonwebtoken";

// Register Validation
const registerValidation = (req, res, next) => {
    const schema = Joi.object({
        username: Joi.string().min(3).max(100).required(),
        email: Joi.string().email().required(),
        password: Joi.string()
            .min(6)
            .max(100)
            .pattern(new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$"))
            .message("Password must contain at least one uppercase, one lowercase, one number, and one special character.")
            .required(),
        mobile: Joi.string()
            .pattern(new RegExp("^[0-9]{10}$"))
            .message("Mobile number must be exactly 10 digits.")
            .required()
    });

    const { error } = schema.validate(req.body);
    if (error) {
        return res.status(400).json({ message: "Bad Request", error: error.details[0].message });
    }
    next();
};

// Login Validation
const loginValidation = (req, res, next) => {
    const schema = Joi.object({
        email: Joi.string().email().required(),
        password: Joi.string().min(6).max(100).required()
    });

    const { error } = schema.validate(req.body);
    if (error) {
        return res.status(400).json({ message: "Bad Request", error: error.details[0].message });
    }
    next();
};

const authenticateUser = (req, res, next) => {
    const token = req.header("Authorization");

    if (!token) {
        return res.status(401).json({ message: "Access denied. No token provided." });
    }

    try {
        const decoded = jwt.verify(token, "your_secret_key");
        req.user = decoded; // Extracts user ID from token
        next();
    } catch (error) {
        res.status(400).json({ message: "Invalid token" });
    }
};

export { registerValidation, loginValidation,authenticateUser };
