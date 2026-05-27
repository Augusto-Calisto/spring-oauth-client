import { createContext, useContext, useEffect, useState } from "react";

const AuthContext = createContext({
    isAuthenticated: false,
    loading: false,
    user: null,
    logout: () => {}
})

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const [loading, setLoading] = useState(true);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [user, setUser] = useState(null);

    useEffect(() => {
        fetchUser();
    }, []);

    const fetchUser = () => {
        fetch("http://localhost:8080/user/info", { credentials: "include" })
            .then((response) => {
                if(response.ok) {
                    return response.json();
                }
            })
            .then((data) => {
                setUser(data);
                setIsAuthenticated(true);
            })
            .catch((e) => {
                throw new Error(e);
            })
            .finally(() => {
                setLoading(false);
            });
    }

    const logout = () => {
        setIsAuthenticated(false);
        setUser(null);
        window.location.href = "http://localhost:8080/logout";
    }

    return (
        <AuthContext.Provider value={{ isAuthenticated, loading, user, logout }}>
            { children }
        </AuthContext.Provider>
    )
}