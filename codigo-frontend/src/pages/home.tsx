import useCodigo from "../context/codigo-context";

export default function Home() {
  const { user } = useCodigo();
  if (!user) {
    return <div>
      You Need to login to use this 
      
    </div>
  }
  return <div></div>;
}
