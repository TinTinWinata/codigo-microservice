import { createContext, ReactNode, useContext, useState } from "react";
import { User } from "../types/user";
import axios from "axios";

interface CodigoContextProps {
  login: (user: User) => void;
  user: User | undefined;
}

const context = createContext<CodigoContextProps>({} as CodigoContextProps);

const API_URL = import.meta.env.VITE_APP_API_URL;

export function CodigoProvider({ children }: { children: ReactNode }) {
  const loadLocalStorage = () => {
    const user = localStorage.getItem("user");
    if (user) {
      return JSON.parse(user);
    }
  };
  const [user, setUser] = useState<User>(loadLocalStorage());

  const login = async (user: User) => {
    const response = await axios.post(API_URL + "/login", user);
    if (response.status === 200) {
      setUser(response.data);
      localStorage.setItem("user", JSON.stringify(response.data));
    }
  };

  return (
    <context.Provider value={{ login, user }}>{children}</context.Provider>
  );
}

export default function useCodigo() {
  return useContext(context);
}
