package ru.yandex.practicum.filmorate.service.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.jdbc.MpaDao;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService{
    private final MpaDao mpaDao;
    @Override
    public Optional<Mpa> getMpaById(int id) {
        return mpaDao.getMpaById(id);
    }

    @Override
    public List<Mpa> getAllMpa() {
        return mpaDao.getAllMpa();
    }

}
