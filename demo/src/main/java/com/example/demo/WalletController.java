package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class WalletController {
    @Autowired
    private WalletRepository walletRepository;

    @GetMapping("/")
    public List<Wallet> GetWallets() {
        return walletRepository.findAll();
    }

    @GetMapping("/{id}")
    public Wallet GetWallet(@PathVariable Integer id) {
        return walletRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Wallet PostWallet(@RequestBody Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @PostMapping("/add/{id}/{money}")
    public void AddMoney(@PathVariable Integer id, @PathVariable Integer money) {
        Wallet wallet = walletRepository.findById(id).orElse(null);
        wallet.setBalance(wallet.getBalance()+money);
        walletRepository.save(wallet);
    }

    @PostMapping("/withdraw/{id}/{money}")
    public String WithdrawMoney(@PathVariable Integer id, @PathVariable Integer money) {
        Wallet wallet = walletRepository.findById(id).orElse(null);
        int currentMoneyInWallet = wallet.getBalance();
        if(currentMoneyInWallet - money > 0)
        {
            wallet.setBalance(currentMoneyInWallet - money);
            walletRepository.save(wallet);
            return "Success";
        }
        return "Insufficient balance in wallet. Cannot withdraw.";
    }

    @PutMapping("/")
    public Wallet PutWallet(@RequestBody Wallet wallet) {
        Wallet oldWallet = walletRepository.findById(wallet.getId()).orElse(null);
        oldWallet.setName(wallet.getName());
        oldWallet.setBalance(wallet.getBalance());
        return walletRepository.save(oldWallet);
    }

    @DeleteMapping("/{id}")
    public Integer deleteWallet(@PathVariable Integer id) {
        walletRepository.deleteById(id);
        return id;
    }
}
