import sys
import subprocess

def is_git_installed() -> bool:
    try:
        subprocess.run(["git", "--version"], capture_output=True, check=True)
        return True
    except Exception:
        return False

if __name__ == "__main__":
    if not is_git_installed():
        print("ERROR: Git is not installed.")
        sys.exit(1)